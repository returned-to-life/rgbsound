package silence.rgbsound.link;

import silence.rgbsound.link.messages.TestsetMapResponce;

public interface Communicator {
    TestsetMapResponce GetTestsetMap(int mapIndex);
}
