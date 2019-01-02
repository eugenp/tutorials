package com.baeldung.stringpool;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * 测试：String—pool常量池
 */
public class StringPoolUnitTest {

    /**
     * 2个字符串常量是相等的
     */
    @Test
    public void whenCreatingConstantStrings_thenTheirAddressesAreEqual() {
        String constantString1 = "Baeldung";
        String constantString2 = "Baeldung";

        assertThat(constantString1).isSameAs(constantString2);
    }

    /**
     * 2个字符串对象是不相等的
     */
    @Test
    public void whenCreatingStringsWithTheNewOperator_thenTheirAddressesAreDifferent() {
        String newString1 = new String("Baeldung");
        String newString2 = new String("Baeldung");

        assertThat(newString1).isNotSameAs(newString2);
    }

    /**
     * 字符串常量和字符串对象相比是不相等的
     */
    @Test
    public void whenComparingConstantAndNewStrings_thenTheirAddressesAreDifferent() {
        String constantString = "Baeldung";
        String newString = new String("Baeldung");

        assertThat(constantString).isNotSameAs(newString);
    }

    /**
     * 执行{@link String#intern()}方法后，会在pool常量池中添加一个当前字符串对象对应的字符串常量
     */
    @Test
    public void whenInterningAStringWithIdenticalValueToAnother_thenTheirAddressesAreEqual() {
        String constantString = "interned Baeldung";
        String newString = new String("interned Baeldung");

        assertThat(constantString).isNotSameAs(newString);

        String internedString = newString.intern();

        assertThat(constantString).isSameAs(internedString);
    }
}
