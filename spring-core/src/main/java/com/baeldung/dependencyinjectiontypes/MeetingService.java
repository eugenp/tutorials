package com.baeldung.dependencyinjectiontypes;

import java.util.Collection;

public class MeetingService {

    private MeetingDao meetingDao;

    public void setMeetingDao(MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    public Collection<Object> getMeetings() {
        return meetingDao.getMeetings();
    }

}
