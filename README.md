VFS-JAVA
========

Virtual File System example using commons VFS library 
Virtual File System is an abstraction layer designed to simplify the programmatic access to file system resources.  VFS reflects a real file system by hiding some of the inner details by providing abstraction. This gives the user flexibility to navigate arbitrary structures on the file system.

Commons VFS provides a single API for accessing various different file systems. I am using this library to demonstrate a simple VFS creation with the following operations. Allowing user to choose an option to use the VFS as needed.

This API is designed for windows operating system and creates the root of the VFS to be in  /Users/Public folder (String vfsPath = “Users/Public/VFS"). 
For execution on Linux system change the vfsPath value to  "/home/user"

1. Create a VFS root Directory. 
2. Display list of operations that can be performed on the newly created VFS
Operations that can be performed are their API’S :-
	1. Create a folder -- createFolder(String path);
Creates the folder in the present working directory and makes it the present working directory.

	2. Create and write in a file -- createWriteFile(String path);
Creates a new file in the current folder, If file with same name exists its overwritten by the new file.

	3. Read the contents of a file--readFile(String path);
Opens the selected file and displays its contents.

	4. List the contents of a folder--listContents(String path);
	Displays the contents of the current folder.

	5. Enter into child folder of choice from the parent directory--enterFolder(String path);
	Allows the user to navigate into the folder by entering the folder name.

	6. Return Back to parent directory--backToParent(String path);
	Allows the user to navigate back to the parent directory from current, if there exists one.

	7. Copy files from local filesystem to VFS--copyToVFS(String path);
	Copies file from local filesystem to current directory of the VFS.

	8. Delete File or folder from VFS--delete(String path);
 	Deletes the file selected by the user.

	9. Exit from VFS
	Closes the VFS application.





