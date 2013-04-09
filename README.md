VFS-JAVA
========

Virtual File System example using commons VFS library 
Virtual File System is an abstraction layer designed to simplify the programmatic access to file system resources.  VFS reflects a real file system by hiding some of the inner details by providing abstraction. This gives the user flexibility to navigate arbitrary structures on the file system.

Commons VFS provides a single API for accessing various different file systems. I am using this library to demonstrate a simple VFS creation with the following operations. Allowing user to choose an option to use the VFS as needed.

1. Create a VFS root Directory.
2. Display list of operations that can be performed on the newly created VFS
Operations that can be performed are and their API'S:-
  1. Create a folder -- createFolder(String path);
	2. Create and write in a file -- createWriteFile(String path);
	3. Read the contents of a file--readFile(String path);
	4. List the contents of a folder--listContents(String path);
	5. Enter into child folder of choice from the parent directory--enterFolder(String path);
	6. Return Back to parent directory--backToParent(String path);
	7. Copy files from local filesystem to VFS--copyToVFS(String path);
	8. Delete File or folder from VFS--delete(String path);
	9. Exit from VFS



