public class FileData {

    public String name;
    public String dir;
    public String lastModifiedDate;

    public FileData(String name, String directory, String modifiedDate) {
    	this.name = name;
    	this.dir = directory;
    	this.lastModifiedDate = modifiedDate;
    }

    public String toString() {
    	return "{Name: " +name+ ", Directory: " +dir+ ", Modified Date: " +lastModifiedDate+ "}";

    }
    
    public boolean similar(FileData other){
        return this.name.equals(other.name) && this.dir.equals(other.dir) ;
    }
    
    public boolean equals(FileData other) {
    	return this.name.equals(other.name) && this.dir.equals(other.dir) && this.lastModifiedDate.equals(other.lastModifiedDate);
    }
    public boolean moreRecent(FileData other){
        return this.lastModifiedDate.compareTo(other.lastModifiedDate) > 0;
    }
}
