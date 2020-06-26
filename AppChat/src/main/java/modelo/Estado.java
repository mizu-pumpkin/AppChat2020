package modelo;

public class Estado {
	
// ---------------------------------------------------------------------
//			                                                  Attributes
// ---------------------------------------------------------------------
		
	private String text;
	private String picture;
	
// ---------------------------------------------------------------------
//			                                                Constructors
// ---------------------------------------------------------------------
			
	public Estado(String text, String picture) {
		this.text = text;
		this.picture = picture;
	}
	
// ---------------------------------------------------------------------
//		                                             Getters and Setters
// ---------------------------------------------------------------------
		
	public String getText() {
		return text;
	}

	public String getPicture() {
		return picture;
	}
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------
		
	@Override
	public String toString() {
		return "Estado [text=" + text + ", picture=" + picture + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
