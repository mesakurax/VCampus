import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;

public class AudioFormatUtils {

    public static void main(String[] args) {
        // ��ȡ������Ƶ�豸
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();

        // ������Ƶ�豸
        for (Mixer.Info mixerInfo : mixerInfos) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);

            // ��ȡĿ����������Ϣ
            Line.Info[] targetLineInfos = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : targetLineInfos) {
                if (lineInfo instanceof DataLine.Info) {
                    DataLine.Info dataLineInfo = (DataLine.Info) lineInfo;
                    if (dataLineInfo.isFormatSupported(getAudioFormat())) {
                        System.out.println("Supported target line format: " + dataLineInfo.getFormats()[0]);
                    }
                }
            }

            // ��ȡԴ��������Ϣ
            Line.Info[] sourceLineInfos = mixer.getSourceLineInfo();
            for (Line.Info lineInfo : sourceLineInfos) {
                if (lineInfo instanceof DataLine.Info) {
                    DataLine.Info dataLineInfo = (DataLine.Info) lineInfo;
                    if (dataLineInfo.isFormatSupported(getAudioFormat())) {
                        System.out.println("Supported source line format: " + dataLineInfo.getFormats()[0]);
                    }
                }
            }
        }
    }

    // �����������Ƶ��ʽ
    public static AudioFormat getAudioFormat() {
        AudioFormat format = new AudioFormat(8000f, 16, 1, true, false);
        return format;
    }
}