package au.com.silverquest.flexigroup.util;

import au.com.bytecode.opencsv.CSVWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Calvin, Stephanie
 * Date: 21/02/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static byte[] extractBytes(InputStream image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }

    public static void writeToCSV(String filePath, List<String[]> data) {
        CSVWriter writer = null;
        try {
            File file = new File(filePath);
            file.createNewFile();
            writer =  new CSVWriter(new FileWriter(file));

            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (Exception e2) {

            }
        }
    }
}
