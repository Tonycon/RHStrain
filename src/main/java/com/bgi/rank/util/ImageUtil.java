package com.bgi.rank.util;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import sun.misc.*;


/**  
 * Title:ImageUtil.java
 * Description： 图片处理工具类
 * Author:tanyuanyi
 * Date:2013-2-26 下午02:06:34
 */
public class ImageUtil {

	
	 /**
     * 图片缩放
     * @param filePath 图片原路径
     * @param outputFilePath 图片输出路径
     * @param height 高度
     * @param width 宽度
     * @param bb 比例不对时是否需要补白
*/
    public static byte[] resize(byte[] file,int height, int width, boolean bb) {
    	ByteArrayOutputStream bos = null;
        try {
            double ratio = 0; //缩放比例    
               
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(file));
            
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);   
            //计算比例   
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {   
                if (bi.getHeight() > bi.getWidth()) {   
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();   
                } else {   
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();   
                }   
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);   
                itemp = op.filter(bi, null);   
            }   
            if (bb) {   
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
                Graphics2D g = image.createGraphics();   
                g.setColor(Color.white);   
                g.fillRect(0, 0, width, height);   
                if (width == itemp.getWidth(null))   
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),  null);   
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null),  null);   
                g.dispose();   
                itemp = image;   
            }else{   // 相当于调用补白的方法，一样的
            	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
                Graphics2D g = image.createGraphics();   
                g.setColor(Color.white);   
                g.fillRect(0, 0, width, height);   
                if (width == itemp.getWidth(null))   
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), null);   
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null),null);   
                g.dispose();   
                itemp = image;    
            }
            bos = new ByteArrayOutputStream();   
            ImageIO.write((BufferedImage) itemp,"jpeg", bos);  
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       if(bos != null) return bos.toByteArray();
       return null;
    }
    
   /**
    * 压缩图片
    * @param file 图片文件的字节数组
    * @param times 压缩倍数
    * @param bb 比例不对时是否需要补白
    * @return
    */
    public static byte[] resize(byte[] file,int times, boolean bb) {
    	ByteArrayOutputStream bos = null;
        try {
            double ratio = (new Integer(1)).doubleValue()/times; //缩放比例    
           
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(file));
            int width = Double.valueOf((ratio * bi.getWidth())).intValue();
            int height = Double.valueOf((ratio * bi.getHeight())).intValue();
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);   
            //计算比例   
            /*if ((bi.getHeight() > height) || (bi.getWidth() > width)) {   
                if (bi.getHeight() > bi.getWidth()) {   
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();   
                } else {   
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();   
                }   
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);   
                itemp = op.filter(bi, null);   
            }   */
            if (bb) {   
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
                Graphics2D g = image.createGraphics();   
                g.setColor(Color.white);   
                g.fillRect(0, 0, width, height);   
                if (width == itemp.getWidth(null))   
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),  null);   
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null),  null);   
                g.dispose();   
                itemp = image;   
            }else{   // 相当于调用补白的方法，一样的
            	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
                Graphics2D g = image.createGraphics();   
                g.setColor(Color.white);   
                g.fillRect(0, 0, width, height);   
                if (width == itemp.getWidth(null))   
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), null);   
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null),null);   
                g.dispose();   
                itemp = image;    
            }
            bos = new ByteArrayOutputStream();   
            ImageIO.write((BufferedImage) itemp,"jpeg", bos);  
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       if(bos != null) return bos.toByteArray();
       return null;
    }
    
    
    @SuppressWarnings("restriction")
	public static String byteArrayToString(byte[] b) {
    	BASE64Encoder encoder = new BASE64Encoder();
    	return encoder.encode(b);
    }
    
    @SuppressWarnings("restriction")
	public static byte[] StringToByteArray(String s) {
    	BASE64Decoder decoder = new BASE64Decoder();
    	byte[] b = new byte[1];
    	try {
			b =  decoder.decodeBuffer(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return b;
    }
    
    public static void main(String[] args) throws IOException {
		File f = new File("f:/3.jpg");
		InputStream is = new FileInputStream(f);
		byte[] b = new byte[(int)f.length()];
		is.read(b);
		byte[] rb = ImageUtil.resize(b,2, true);
		FileOutputStream fos = new FileOutputStream(new File("f:/31.jpg"));
		fos.write(rb);
		is.close();
		fos.close();
	}
}
