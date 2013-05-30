package de.dailab.plistacontest.recommender;

public class ContestItem {

	long id;
	String description;
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String _description) {
		this.description = _description;
	}

	public ContestItem(final long _id) {
		this.id = _id;
	}
	
	public ContestItem(final long _id, final String _description) {
		this.id = _id;
		this.description = _description;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof ContestItem)
        {
            sameSame = this.id == ((ContestItem) object).id;
        }

        return sameSame;
    }
	
}
