package com.baeldung.nested

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ComputerUnitTest {

    @Test
    fun givenComputer_whenPowerOn_thenBlink() {
        val computer = Computer("Desktop")

        assertThat(computer.powerOn()).isEqualTo("blinking Green")
    }

    @Test
    fun givenMotherboard_whenGetInfo_thenGetInstalledAndBuiltDetails() {
        val motherBoard = Computer.MotherBoard("MotherBoard Inc.")

        assertThat(motherBoard.getInfo()).isEqualTo("Made by MotherBoard Inc. installed in China - 2018-05-23")
    }

    @Test
    fun givenHardDisk_whenGetInfo_thenGetComputerModelAndDiskSizeInGb() {
        val hardDisk = Computer("Desktop").HardDisk(1000)

        assertThat(hardDisk.getInfo()).isEqualTo("Installed on Computer(model=Desktop) with 1000 GB")
    }
}