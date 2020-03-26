package fr.esilv.finalproject_riad_benradi;

public class KitsuSearchData
{
    private int id ;
    private String type;
    private Attributes attributes;
    private relationships relationships;

    public Attributes getAttributes() {
        return attributes;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public fr.esilv.finalproject_riad_benradi.relationships getRelationships() {
        return relationships;
    }
}
