package pl.mslawin.ocadofm.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mslawin on 18.04.15.
 */
@DatabaseTable
public class Team {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private HashSet<String> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getMembers() {
        return members;
    }

    public void setMembers(HashSet<String> members) {
        this.members = members;
    }
}