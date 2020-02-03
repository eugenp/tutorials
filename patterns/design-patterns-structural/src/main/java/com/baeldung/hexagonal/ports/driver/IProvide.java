package com.baeldung.hexagonal.ports.driver;

import java.util.List;

public interface IProvide<INPUT, OUTPUT> {

    List<OUTPUT> list();
}
