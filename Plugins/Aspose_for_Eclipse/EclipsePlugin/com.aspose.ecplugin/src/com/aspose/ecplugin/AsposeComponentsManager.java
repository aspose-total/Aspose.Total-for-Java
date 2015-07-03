/*
 * Copyright (c) 2001-2013 Aspose Pty Ltd. All Rights Reserved.
 * Author: Mohsan.Raza
 */
package com.aspose.ecplugin;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import com.aspose.componentsdownload.ObjectFactoryComponents;
import com.aspose.componentsdownload.ProductRelease;
import com.aspose.ecplugin.AsposeJavaComponent;
import com.aspose.ecplugin.AsposeJavaComponents;
import com.aspose.ecplugin.wizard.WizardNewProjectCreationPageCustom;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;

/**
 * 
 * @author mohsan.raza
 *
 */
public class AsposeComponentsManager {
	WizardNewProjectCreationPageCustom _pageOne = null;
	public AsposeComponentsManager(WizardNewProjectCreationPageCustom page)
	{
		_pageOne = page;
	}
	 private boolean result=false;
	/**
	 * 
	 * @return
	 */
	 /**
	  * Modified By: Adeel Ilyas (9/5/2014)
	  * 
	  */
	public boolean downloadComponents()
	{
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(null); 
		
		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) { 
					 
					 
			
			if(!isIneternetConnected())
			{
				_pageOne.showMessage(AsposeConstants.INTERNET_CONNECTION_REQUIRED_MESSAGE_TITLE, AsposeConstants.INTERNET_CONNECTION_REQUIRED_MESSAGE, SWT.ICON_WARNING | SWT.OK);
				 result=false;
				
			} else result=true;
			
			if (result) {
				monitor.beginTask("Downloading selected Aspose APIs ...",Integer.MAX_VALUE); 
			
				monitor.subTask("Preparing to download selected APIs");
				long totalbytes=0;
				
				for(AsposeJavaComponent component:AsposeJavaComponents.list.values())
				{
				
					if(component.is_selected())
					{
					try {	
						ProductRelease productRelease = getProductReleaseInfo(component.get_downloadUrl());
						component.set_downloadUrl(productRelease.getDownloadLink());
						component.set_downloadFileName(productRelease.getFileName());
						component.set_changeLog(productRelease.getChangeLog());
						component.set_latestVersion(productRelease.getVersionNumber());
						component.set_downloadlength(getFileDownloadLength(component.get_downloadUrl()));
						totalbytes+=component.get_downloadlength();
					} catch (StringIndexOutOfBoundsException ex) {
						component.set_selected(false);
					}
					}
				}
				monitor.worked(350);
				double unitsperbyte = (Integer.MAX_VALUE-350)/totalbytes; 
				
				
				for(AsposeJavaComponent component:AsposeJavaComponents.list.values())
			{
			if (!monitor.isCanceled()) {
				if(component.is_selected())
				{
					
					if(libraryAlreadyExists(component.get_downloadFileName()))
					{
						String currentComponentVersion = readVersion(component);
						component.set_currentVersion(currentComponentVersion);
						
						if(currentComponentVersion != null && currentComponentVersion.equals(component.get_latestVersion())  )
						{

							component.set_downloaded(true);
							monitor.worked((int) Math.round(unitsperbyte*component.get_downloadlength()));
							//storeVersion(component);
						}
						else
						{
							storeReleaseNotes(component);
							String htmlFilePath = getLibaryDownloadPath()+  component.get_name() + ".htm"; // path to your new file
							File htmlFile = new File(htmlFilePath);

							// open the default web browser for the HTML page
							try {
								Desktop.getDesktop().browse(htmlFile.toURI());
							} catch (IOException e) {
								e.printStackTrace();
							}

							try {
								Desktop.getDesktop().open(htmlFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							if(_pageOne.showMessage(component.get_name()+ " - " + AsposeConstants.NEW_VERSION_MESSAGE_TITLE, AsposeConstants.NEW_VERSION_MESSAGE +"\nLatest Version: "+ component.get_latestVersion() +"\nCurrent Version: "+ component.get_currentVersion() , SWT.ICON_INFORMATION | SWT.YES|SWT.NO) == SWT.YES)
							{
								if(downloadFileFromInternet(component.get_downloadUrl(), component.get_downloadFileName(),component.get_name(),monitor,unitsperbyte))
								{
									component.set_downloaded(true);
									storeVersion(component);
									
								}	
							}
							else
							{
								component.set_downloaded(true);
								monitor.worked((int) Math.round(unitsperbyte*component.get_downloadlength()));
							}
						}
					}
					else
					{
						if(downloadFileFromInternet(component.get_downloadUrl(), component.get_downloadFileName(),component.get_name(),monitor,unitsperbyte))
						{
							component.set_downloaded(true);
							storeVersion(component);
						}
						else
						{
							result=false;
							
						}
					}
				}
			} else {
			monitor.subTask("Cancelling..");
				break;
				
			}}
				}
			monitor.done();
				}});
		} catch (InvocationTargetException e) {
			result=false;
			e.printStackTrace();
		} catch (InterruptedException e) {
			result=false;
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 
	 * @param component
	 * @return
	 */
	public String readVersion(AsposeJavaComponent component)
	{
		String localPath = getLibaryDownloadPath()+  component.get_name() + ".ver";
		BufferedReader reader;
		String line = null;

		try {
			reader = new BufferedReader(new FileReader(localPath));
			line = reader.readLine();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return  line;
	}

	/**
	 * 
	 * @param component
	 */
	public void storeReleaseNotes(AsposeJavaComponent component)
	{
		String localPath = getLibaryDownloadPath()+  component.get_name() + ".htm";
		PrintWriter writer;
		try {
			writer = new PrintWriter(localPath, "UTF-8");
			writer.println(component.get_changeLog());
			writer.close();
		} catch (FileNotFoundException e) {
			//writer.close();
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			//writer.close();
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param component
	 */
	public void storeVersion(AsposeJavaComponent component)
	{
		String localPath = getLibaryDownloadPath()+  component.get_name() + ".ver";
		PrintWriter writer;
		try {
			writer = new PrintWriter(localPath, "UTF-8");
			writer.println(component.get_latestVersion());
			writer.close();
		} catch (FileNotFoundException e) {
			//writer.close();
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			//writer.close();
			e.printStackTrace();
		}

	}
	
	 /**
    *
    * @param productUrl
    * @return
    */
   public ProductRelease getProductReleaseInfo(String productUrl) {

       ProductRelease data = null;
       try {
           String productInfo;
           productInfo = readURLContents(productUrl);
           productInfo = productInfo.substring(40);
           JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactoryComponents.class);
           Unmarshaller unmarshaller;
           unmarshaller = jaxbContext.createUnmarshaller();
           data = (ProductRelease) unmarshaller.unmarshal(new StreamSource(new StringReader (productInfo.toString())));
       } catch (StringIndexOutOfBoundsException ex) {
    	   throw ex;
    	   
       }  catch (Exception ex) {
           ex.printStackTrace();
       }

       return data;
   }
   public String readURLContents(String Url) throws MalformedURLException, IOException {
       URL url = new URL(Url);
       URLConnection con = url.openConnection();
       InputStream in = con.getInputStream();
       String encoding = con.getContentEncoding();
       encoding = encoding == null ? "UTF-8" : encoding;
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       byte[] buf = new byte[8192];
       int len = 0;
       while ((len = in.read(buf)) != -1) {
           baos.write(buf, 0, len);
       }
       String body = new String(baos.toByteArray(), encoding);
       return body;
   }
	/**
	 * 
	 * @param libFileName
	 * @return
	 */
	private boolean libraryAlreadyExists(String libFileName)
	{
		File confirmPath = new File(getLibaryDownloadPath() + libFileName);
		if(confirmPath.exists())
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param urlStr
	 * @return
	 */
	private long getFileDownloadLength(String urlStr)
	{
		URL url;
		long fileLenth=0;
		try {
			url = new URL(urlStr);

			URLConnection connection = url.openConnection();
			connection.connect();

			fileLenth = connection.getContentLength();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileLenth;
	}
	/**
	 * 
	 * @param urlStr
	 * @param outputFile
	 * @return
	 */
	public boolean downloadFileFromInternet(String urlStr, String outputFile,String componentName, IProgressMonitor monitor,double unitsperbyte) {
		monitor.subTask(componentName);
		InputStream input;
		int bufferSize = 4096;
		String localPath = getLibaryDownloadPath();
		try {
			URL url = new URL(urlStr);
			input = url.openStream();
			byte[] buffer = new byte[bufferSize];
			File f = new File(localPath + outputFile);
			OutputStream output = new FileOutputStream(f);
			int bytes = 0;

			int currentPage = 0;
			try {
				int bytesRead;
				while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
					output.write(buffer, 0, bytesRead);
					bytes = bytes + buffer.length;
					currentPage =currentPage + 1;
					if (monitor.isCanceled()) {
						monitor.subTask("Cancelling...");
					}
					monitor.worked((int) Math.round(unitsperbyte*bytesRead));

				}               

				output.flush();
				output.close();
				extractFolder(localPath + outputFile, localPath + removeExtention(outputFile) );

			} finally {

			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String removeExtention(String filePath) {
		File f = new File(filePath);
		if (f.isDirectory()) return filePath;
		String name = f.getName();
		final int lastPeriodPos = name.lastIndexOf('.');
		if (lastPeriodPos <= 0)
		{
			return filePath;
		}
		else
		{
			File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
			return renamed.getPath();
		}
	}
	/**
	 * 
	 * @return
	 */
	public static boolean isIneternetConnected()
	{
		try {
			InetAddress address = InetAddress.getByName(AsposeConstants.INTERNTE_CONNNECTIVITY_PING_URL);
			if(address == null)
			{
				return false;
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	public static String getLibaryDownloadPath()
	{
		String path = "";
		path = System.getProperty("user.home");
		path = path + "/aspose/ecplugin/";
		File confirmPath = new File(path);
		if(!confirmPath.exists())
			new File(path).mkdirs();
		return path;
	}

	public static String getAsposeHomePath()
	{
		String path = "";
		path = System.getProperty("user.home");
		path = path + "/aspose/";
		return path;
	}
	/**
	 * 
	 * @param zipFile
	 * @param outputFolder
	 */
	public void unZipFile(String zipFile, String outputFolder){

		byte[] buffer = new byte[1024];

		try{
			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}
			ZipInputStream zis = 
					new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
			while(ze!=null){

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				System.out.println("file unzip : "+ newFile.getAbsoluteFile());
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);             
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();   
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		}catch(IOException ex){
			ex.printStackTrace(); 
		}
	} 

	/**
	 * 
	 * @param zipFile
	 * @param newPath
	 * @throws ZipException
	 * @throws IOException
	 */
	static public void extractFolder(String zipFile,String newPath) throws ZipException, IOException 
	{
		System.out.println(zipFile);
		int BUFFER = 2048;
		File file = new File(zipFile);

		ZipFile zip = new ZipFile(file);
		new File(newPath).mkdir();
		@SuppressWarnings("rawtypes")
		Enumeration zipFileEntries = zip.entries();
		while (zipFileEntries.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(newPath, currentEntry);
			File destinationParent = destFile.getParentFile();
			destinationParent.mkdirs();

			if (!entry.isDirectory())
			{
				BufferedInputStream is = new BufferedInputStream(zip
						.getInputStream(entry));
				int currentByte;
				byte data[] = new byte[BUFFER];

				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);
				while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
			}
		}
	}

}
