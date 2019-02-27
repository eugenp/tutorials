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

        new File('src/main/resources/ioData.txt').withDataInputStream { is ->
            assertEquals(message, is.readUTF())
            assertEquals(length, is.readInt())
            assertEquals(valid, is.readBoolean())
        }
    }

    @Test
    void whenUsingWithObjectOutputStream_thenObjectIsSerializedToFile() {
        Task task = new Task(description:'Take out the trash', startDate:new Date(), status:0)
        new File('src/main/resources/ioSerializedObject.txt').withObjectOutputStream { out ->
            out.writeObject(task)
        }

        new File('src/main/resources/ioSerializedObject.txt').withObjectInputStream { is ->
            Task taskRead = is.readObject()
            assertEquals(task.description, taskRead.description)
            assertEquals(task.startDate, taskRead.startDate)
            assertEquals(task.status, taskRead.status)
        }
    }
}
