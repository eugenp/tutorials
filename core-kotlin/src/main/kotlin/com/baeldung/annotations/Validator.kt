package com.baeldung.annotations

/**
 * Naive annotation-based validator.
 * @author A.Shcherbakov
 */
class Validator() {

    /**
     * Return true if every item's property annotated with @Positive is positive and if
     * every item's property annotated with @AllowedNames has a value specified in that annotation.
     */
    fun isValid(item: Item): Boolean {
        val fields = item::class.java.declaredFields
        for (field in fields) {
            field.isAccessible = true
            for (annotation in field.annotations) {
                val value = field.get(item)
                if (field.isAnnotationPresent(Positive::class.java)) {
                    val amount = value as Float
                    if (amount < 0) {
                        return false
                    }
                }
                if (field.isAnnotationPresent(AllowedNames::class.java)) {
                    val allowedNames = field.getAnnotation(AllowedNames::class.java)?.names
                    val name = value as String
                    allowedNames?.let {
                        if (!it.contains(name)) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }
}