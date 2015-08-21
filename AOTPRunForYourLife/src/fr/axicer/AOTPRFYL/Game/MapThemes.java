package fr.axicer.AOTPRFYL.Game;



public enum MapThemes {
	MEDIEVAL("maps/medieval"),
	FANTASTIC("maps/fantastic"),
	MODERN("maps/modern"),
	SPACE("maps/space"),
	JUNGLE("maps/jungle"),
	VOLCANO("maps/volcano");
	
	private String path;
	
	MapThemes(String path) {
		this.setPath(path);
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
