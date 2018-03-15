package javaday.istanbul.sliconf.micro.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by ttayfur on 13/3/17.
 */
@Document(collection = "userSchedules")
@CompoundIndexes(@CompoundIndex(def = "{'id':1}"))
@Getter
@Setter
public class UserScheduleElement {
    @Id
    private String id;
    private String eventId;
    private String sessionId;
    private String userId;
}