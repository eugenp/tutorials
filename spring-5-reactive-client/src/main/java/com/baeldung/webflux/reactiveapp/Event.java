package com.baeldung.webflux.reactiveapp;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Event implements Serializable {
        private int id;
        private long time;

        public Event(int id, long time) {
                this.id = id;
                this.time = time;
        }
}
