package fr.axicer.AOTPRFYL.Game;



public enum MapThemes {
	MEDIEVAL("/maps/medieval","medieval"),
	FANTASTIC("/maps/fantastic","fantastic"),
	MODERN("/maps/modern","modern"),
	SPACE("/maps/space","space"),
	JUNGLE("/maps/jungle","jungle"),
	VOLCANO("/maps/volcano","volcano");
	
	private String path;
	private String name;
	
	MapThemes(String path, String name) {
		this.setPath(path);
		this.setName(name);
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static MapThemes getByName(String name){
		for(MapThemes theme: MapThemes.values()){
			if(theme.getName().equalsIgnoreCase(name)){
				return theme;
			}
		}
		return MEDIEVAL;
	}
}
