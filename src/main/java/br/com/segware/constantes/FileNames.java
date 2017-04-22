package br.com.segware.constantes;

public enum FileNames {
	RELATORIO("src\\test\\java\\br\\com\\segware\\", "relatorio.csv");
	
	FileNames(String path, String name){
		this.path = path;
		this.name = name;
	}
	
	private String name;
	private String path;
	
	public String getFile() {
		return this.path + this.name;
	}
}
