import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.DefaultFileContent;
import org.apache.commons.vfs2.provider.UriParser;

public class VirtualFS {
  private static String pwd; 
	private static final String vfsPath = "C:/Users/Public/VFS";
	private static InputStreamReader is;
	private static BufferedReader br;

	public static void main(String[] args) throws IOException {
		/* Creation of root directory for VFS */
		is = new InputStreamReader(System.in);
		br = new BufferedReader(is);
		FileSystemManager fsManagerF = VFS.getManager();
		FileObject jarFileF = fsManagerF.resolveFile(vfsPath);
		if (!jarFileF.exists()) {
			jarFileF.createFolder();
		}
		pwd = vfsPath;
		listContents(pwd);
		while (true) {
			try {
				/*Displaying Menu Options for the user to select */
				System.out.println("Please follow the menu to use VFS");
				System.out.print("1-create a folder||");
				System.out.print("2-create and write in a file||");
				System.out.print("3-read file||");
				System.out.print("4-List contents||");
				System.out.println("5-Enter into folder||");
				System.out.print("6-back to parent folder||");
				System.out.print("7-copy files from local filesystem to VFS||");
				System.out.print("8-delete a file||");
				System.out.print("9-Exit VFS");

				String line;
				line = br.readLine();
				int optionNumber = Integer.parseInt(line);
				if (optionNumber < 1 || optionNumber > 9) {
					System.out.println("enter option in 1 to 9");
					break;
				}
				switch (optionNumber) {
				case 1:
					createFolder(pwd);
					break;
				case 2:
					createWriteFile(pwd);
					break;
				case 3:
					readFile(pwd);
					break;
				case 4:
					listContents(pwd);
					break;
				case 5:
					enterFolder(pwd);
					break;
				case 6:
					backToParent(pwd);
					break;
				case 7:
					copyToVFS(pwd);
					break;
				case 8:
					delete(pwd);
					break;
				case 9:
					br.close();
					is.close();
					return;
				}

			} catch (Exception e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static void backToParent(String path) {
		if (path.equals(vfsPath)) {
			System.out.println("No parent , you are in start folder");
			return;
		}
		String[] splitNames = path.split("/");
		StringBuffer newPath = new StringBuffer();
		for (int i = 0; i < splitNames.length - 1; i++) {
			newPath.append('/').append(splitNames[i]);
		}
		pwd = newPath.toString();
		listContents(pwd);

	}

	public static void enterFolder(String path) throws IOException {
		StringBuffer newPath = new StringBuffer(path);
		System.out.println("Enter the folder name");
		newPath.append('/').append(br.readLine());
		pwd = newPath.toString();
		listContents(pwd);
	}

	public static void readFile(String path) {
		try {
			StringBuffer newPath = new StringBuffer(path);
			System.out.println("Enter the filename");
			newPath.append('/').append(br.readLine());
			File file = new File(newPath.toString());
			if (!file.exists())
				throw new RuntimeException(
						"file not found, Create file before opening");
			else {

				FileSystemManager fsManager = VFS.getManager();
				FileObject jarFile = fsManager.resolveFile(newPath.toString());
				FileContent s = jarFile.getContent();
				InputStream is = s.getInputStream();
				BufferedReader brF = new BufferedReader(new InputStreamReader(
						is));

				String line;
				while ((line = brF.readLine()) != null) {
					System.out.println(line);
				}

				brF.close();
				is.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void createFolder(String path) {
		try {
			StringBuffer newPath = new StringBuffer(path);
			System.out.println("Enter the folder name to create");
			newPath.append('/').append(br.readLine());
			pwd = newPath.toString();
			FileSystemManager fsManager = VFS.getManager();
			FileObject jarFile = fsManager.resolveFile(pwd);
			if (jarFile.exists())
				throw new RuntimeException("Folder already exists");
			else
				jarFile.createFolder();
			System.out.println("Folder creation success");
			listContents(pwd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void createWriteFile(String path) {
		try {
			StringBuffer newPath = new StringBuffer(path);
			System.out.println("Enter the file name to create");
			newPath.append('/').append(br.readLine());
			FileSystemManager fsManager = VFS.getManager();
			FileObject jarFile = fsManager.resolveFile(newPath.toString());

			FileContent s = jarFile.getContent();
			OutputStream os = s.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			String line;
			System.out
					.println("File Created Please Enter Content, when Finished type DONE");
			while (!(line = br.readLine()).equals("DONE")) {
				bw.newLine();
				bw.write(line);
			}
			bw.close();
			os.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void listContents(String path) {
		try {
			System.out.println("Content in the present working directory: "
					+ path);
			FileSystemManager fsManager = VFS.getManager();
			FileObject jarFile = fsManager.resolveFile(path);

			FileObject[] children = jarFile.getChildren();
			// System.out.println("Children of " + jarFile.getName().getURI());
			for (int i = 0; i < children.length; i++) {
				System.out.println("----" + children[i].getName().getBaseName()
						+ " ||type: " + children[i].getType().toString());

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	// Method to copy a file from local to VFS
	public static void copyToVFS(String path) throws IOException {
		System.out
				.println("Enter the path of the file in local filesystem that you wish to copy to VFS");
		String usrPath = br.readLine();
		String[] splitNames = usrPath.split("/");
		StringBuffer newPath = new StringBuffer(path);
		newPath.append('/').append(splitNames[splitNames.length - 1]);
		File file = new File(usrPath);
		File remotefile = new File(newPath.toString());
		if (!file.exists())
			throw new RuntimeException("Error. Local file not found");

		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			// Create local file object
			FileObject localFile = manager.resolveFile(file.getAbsolutePath());

			// Create remote file object
			FileObject remoteFile = manager.resolveFile(remotefile
					.getAbsolutePath());

			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);

			System.out.println("File upload success");

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			manager.close();
		}
	}

	// Delete file:
	public static void delete(String path) throws IOException {
		StringBuffer newPath = new StringBuffer(path);
		System.out.println("Enter the file name that you wish to delete");
		newPath.append('/').append(br.readLine());
		// pwd = newPath.toString();
		StandardFileSystemManager manager = new StandardFileSystemManager();
		File remotefile = new File(newPath.toString());
		try {
			// Create remote object
			FileObject remoteFile = manager.resolveFile(remotefile
					.getAbsolutePath());

			if (remoteFile.exists()) {
				remoteFile.delete();
				System.out.println("Delete remote file success");
			}
			listContents(pwd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			manager.close();
		}
	}

}
