package com.baeldung.unnamed.variables;

public record Car<T extends Engine>(String name, String color, T engine) { }

abstract class Engine { }

class GasEngine extends Engine { }

class ElectricEngine extends Engine { }

class HybridEngine extends Engine { }