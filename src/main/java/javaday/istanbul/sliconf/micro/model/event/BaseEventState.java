package javaday.istanbul.sliconf.micro.model.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventStates")
@CompoundIndexes({
        @CompoundIndex(def = "{'id':1}")
})
@Getter
@Setter
public class BaseEventState {
    @Id
    private String id;
    private String name;
    private long roomCount;
    private long sessionCount;
    private long participantCount;
    private boolean dataExportImport;
    private boolean analytics;
    private boolean support;
    private boolean customize;
    private String type;
}