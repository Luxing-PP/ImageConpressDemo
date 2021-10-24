package CompressStrategy;

import net.coobird.thumbnailator.name.Rename;

import java.io.BufferedReader;
import java.io.IOException;

public interface CompressStrategy {
    void ResolvePara(BufferedReader bf) throws IOException;
    void Compress() throws IOException;
}
