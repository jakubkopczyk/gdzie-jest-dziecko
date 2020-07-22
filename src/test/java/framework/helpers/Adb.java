package framework.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Adb {
    static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final int AVC_CODEC_QUALITY_PARAM = 16;
    private static final int FINALIZE_RECORDING_DELAY = 2000;
    private static final Logger logger = LoggerFactory.getLogger(Adb.class.getSimpleName());

    public static String cmd(String adb_cmd, String device_id, Boolean waitForInteraction) {
        String adb_shell_cmd = null;
        boolean isAdbShellCommand = false;
        String output;
        String device_id_cmd = String.format("-s %s", device_id);

        if (adb_cmd.startsWith("adb"))
            adb_cmd = adb_cmd.replaceFirst("adb ", "");
        if (adb_cmd.startsWith("shell ")) {
            isAdbShellCommand = true;
            String strip_substring = "shell ";
            adb_shell_cmd = adb_cmd.substring(strip_substring.length());
        }
        if (isAdbShellCommand)
            adb_cmd = String.format("shell %s", adb_shell_cmd);
        String cmd = String.format("adb %s %s", device_id_cmd, adb_cmd);
        if (waitForInteraction) {
            output = BashCommand.executeBashCommand(cmd);
        } else if (Objects.equals(Thread.currentThread().getStackTrace()[2].getMethodName(), "startRecordingScenario")) {
            output = BashCommand.executeBashCommandWithoutOutput(cmd);
        } else {
            output = BashCommand.executeBashCommandWithoutInteraction(cmd);
        }
        return output;
    }

    public static List<Integer> calc_dimension(String quality, String udid) {
        Map<String, Integer> qualityMap = new HashMap<>();
        List<Integer> qualityDimension = new ArrayList<>();
        Integer qualityScreenSizeToAvc, qualityScreenSize, diffSizeToSixteen, qualityFactor = null;
        quality = quality.toLowerCase();
        String screenSize = Adb.cmd("adb shell wm size", udid, true).replace(" ", "").replace("\n", "");
        String[] screenParameters = screenSize.split(":");
        screenParameters = screenParameters[1].split("x");
        Integer[] dimension = {Integer.valueOf(screenParameters[0]), Integer.valueOf(screenParameters[1])};

        qualityMap.put("high", 1);
        qualityMap.put("medium", 2);
        qualityMap.put("low", 3);
        try {
            qualityFactor = qualityMap.get(quality);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Integer size : dimension) {
            qualityScreenSizeToAvc = size + AVC_CODEC_QUALITY_PARAM - (size % AVC_CODEC_QUALITY_PARAM);
            qualityScreenSize = qualityScreenSizeToAvc / qualityFactor;
            diffSizeToSixteen = AVC_CODEC_QUALITY_PARAM - (qualityScreenSize % AVC_CODEC_QUALITY_PARAM);
            qualityDimension.add((qualityScreenSize + diffSizeToSixteen));
        }
        return qualityDimension;
    }

    public static void startRecordingScenario(String quality, String scenario, String udid, Integer part) {
        String mkdir_cmd = "shell mkdir -p sdcard/videos";
        Adb.cmd(mkdir_cmd, udid, false);
        List<Integer> qualityDimension = calc_dimension(quality, udid);
        String recordingSize = String.format("%dx%d", qualityDimension.get(0), qualityDimension.get(1));
        scenario = scenario.replace(" ", "_").replace("\"", "").toLowerCase();
        String pathToVideo = String.format("/sdcard/videos/%s_part_%s_%s.mp4", scenario, part, ZonedDateTime.now().format(DATETIME_FORMATTER));
        String adb_cmd = String.format("shell screenrecord --size %s %s", recordingSize, pathToVideo);
        Adb.cmd(adb_cmd, udid, false);
    }

    public static String getActiveRecordingPid(String udid) {
        String adb_cmd = "shell ps | grep screenrecord";
        String response = Adb.cmd(adb_cmd, udid, true);
        String res1 = response.trim();
        if (res1.equals("")) {
            return "";
        } else {
            return res1.split("\n")[0].replaceAll(" +", " ").split(" ")[1];
        }
    }

    public static void stopCurrentRecording(String udid) {
        String pid = Adb.getActiveRecordingPid(udid);
        if (pid.isEmpty()) {
            logger.info("Video recording process not found");
        } else {
            logger.info(String.format("STOPPING RECORDING. Process pid: %s", pid));
            String adb_cmd = String.format("shell kill -2 %s", pid);
            Adb.cmd(adb_cmd, udid, false);
            try {
                Thread.sleep(FINALIZE_RECORDING_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void pullScenarioVideo(String scenario, String udid) {
        String adb_cmd = "shell ls /sdcard/videos";
        String result = Adb.cmd(adb_cmd, udid, true);
        String[] resultVideos = result.split("\n");
        for (String videoName : resultVideos) {
            if (videoName.startsWith(scenario.toLowerCase().replace(" ", "_"))) {
                adb_cmd = String.format("pull /sdcard/videos/%s ", videoName) + System.getProperty("user.dir") + "/results";
                logger.info(String.format("Pulling video from Android device: %s", videoName));
                Adb.cmd(adb_cmd, udid, false);
            }
        }

    }

    public static void removePreviousVideos(String udid) {
        String adb_cmd = "shell ls /sdcard/videos";
        String result = Adb.cmd(adb_cmd, udid, true);
        String[] resultVideos = result.split("\n");
        for (String videoName : resultVideos) {
            if (videoName.endsWith(".mp4")) {
                adb_cmd = String.format("adb shell rm -f /sdcard/videos/%s", videoName);
                Adb.cmd(adb_cmd, udid, false);
                logger.info(String.format("Removing video from Android device: %s", videoName));
            }
        }
    }
}
