package com.baeldung.io

import static org.junit.Assert.*

import org.junit.Test

class DataAndObjectsUnitTest {
    @Test
    void whenUsingWithDataOutputStream_thenDataIsSerializedToAFile() {
        String message = 'This is a serialized string'
        int length = message.length()
        boolean valid = true
        new File('src/main/resources/ioData.txt').withDataOutputStream { out ->
            out.writeUTF(message)
            out.writeInt(length)
            out.writeBoolean(valid)
        }

        String loadedMessage = ""
        int loadedLength
        boolean loadedValid
        
        new File('src/main/resources/ioData.txt').withDataInputStream { is ->
            loadedMessage = is.readUTF()
            loadedLength = is.readInt()
            loadedValid = is.readBoolean()
        }
        
        assertEquals(message, loadedMessage)
        assertEquals(length, loadedLength)
        assertEquals(valid, loadedValid)
    }

    @Test
    void whenUsingWithObjectOutputStream_thenObjectIsSerializedToFile() {
        Task task = new Task(description:'Take out the trash', startDate:new Date(), status:0)
        def serializedDataFile = new File('src/main/resources/ioSerializedObject.txt')
        serializedDataFile.createNewFile()
        serializedDataFile.withObjectOutputStream { out ->
            out.writeObject(task)
        }

        Task taskRead
        
        new File('src/main/resources/ioSerializedObject.txt').withObjectInputStream { is ->
            taskRead = is.readObject()
        }
        
        assertEquals(task.description, taskRead.description)
        assertEquals(task.startDate, taskRead.startDate)
        assertEquals(task.status, taskRead.status)
    }
}
