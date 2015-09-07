package extractorL;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by liekkas on 15/6/5.
 */
public class ExtractImageInfo {

    public static void main(String[] args) throws JpegProcessingException, IOException, PngProcessingException {

        File jpegFile = new File(ExtractImageInfo.class.getResource("/066.jpg").getPath());
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
//        Metadata metadata = PngMetadataReader.readMetadata(jpegFile);

        Iterable<Directory> dirs = metadata.getDirectories();
        for (Directory dir : dirs) {
            Collection<Tag> tags = dir.getTags();
            for (Tag tag : tags) {
                System.out.println(tag);
            }
        }

    }
}
