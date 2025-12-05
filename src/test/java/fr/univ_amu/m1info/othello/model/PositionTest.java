package fr.univ_amu.m1info.othello.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void testConstructorAndGetters() {
        Position p = new Position(3, 5);

        assertEquals(3, p.row());
        assertEquals(5, p.column());
    }

    @Test
    void testEqualsSameObject() {
        Position p = new Position(2, 4);

        assertEquals(p, p);   // reflexivity
    }

    @Test
    void testEqualsCorrectly() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);

        assertEquals(p1, p2);   // equality by value
        assertEquals(p1.hashCode(), p2.hashCode()); // contract
    }

    @Test
    void testNotEqualsDifferentRow() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(5, 2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testNotEqualsDifferentColumn() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 9);

        assertNotEquals(p1, p2);
    }

    @Test
    void testNotEqualsNull() {
        Position p = new Position(0, 0);

        assertNotEquals(p, null);
    }

    @Test
    void testNotEqualsOtherType() {
        Position p = new Position(0, 0);

        assertNotEquals(p, "(0,0)"); // different class
    }

    @Test
    void testHashCodeContract() {
        Position p1 = new Position(4, 6);
        Position p2 = new Position(4, 6);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Position p = new Position(3, 5);

        assertEquals("(3,5)", p.toString());
    }
}
