package CompressStrategy;

import net.coobird.thumbnailator.name.Rename;

import java.io.BufferedReader;
import java.io.IOException;

public interface CompressStrategy {
    void Input(BufferedReader bf) throws IOException;
    void Compress() throws IOException;
}
