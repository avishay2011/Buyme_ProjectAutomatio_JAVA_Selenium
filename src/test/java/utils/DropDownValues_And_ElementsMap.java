package utils;


import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DropDownValues_And_ElementsMap {

    public static List<String> getExpectedAmounts() {
        return Arrays.asList(
                "סכום",
                "עד 99 ש\"ח",
                "100-199 ש\"ח",
                "200-299 ש\"ח",
                "300-499 ש\"ח",
                "500-750 ש\"ח",
                "מעל 750 ש\"ח"
        );
    }

    public static List<String> getExpectedRegions() {
        return Arrays.asList(
                "אזור",
                "מרכז",
                "ת\"א והסביבה",
                "צפון",
                "דרום",
                "ירושלים",
                "חיפה",
                "השרון",
                "אילת",
                "פריסה ארצית",
                "עוטף והסביבה"
        );
    }

    public static List<String> getExpectedCategories() {
        return Arrays.asList(
                "קטגוריה",
                "המתנות האהובות של 2025",
                "גיפט קארד למותגי אופנה",
                "משלוחי פרחים",
                "גיפט קארד למתנות ליולדת וצעצועים",
                "מתנות במימוש אונליין",
                "גיפט קארד לבתי ספא",
                "גיפט קארד למסעדות שף",
                "גיפט קארד למסעדות",
                "גיפט קארד לארוחת בוקר ובתי קפה",
                "גיפט קארד לבית, מטבח וגאדג'טים",
                "גיפט קארד לנופש ומלונות",
                "גיפט קארד למתנות קולינריות",
                "גיפט קארד לתרבות ופנאי",
                "גיפט קארד לחוויות משותפות",
                "גיפט קארד לבריאות, ספורט ואקסטרים",
                "גיפט קארד לסדנאות והעשרה",
                "גיפט קארד ליופי וטיפוח"
        );
    }

    public static Map<String, By> getElementsMap() {
        return Map.of(
                "Gift Receiver Name Field", By.id("שם מקבל המתנה-friendName"),
                "Celebration Dropdown Button", By.cssSelector(".b2c-dropdown__wrapper__icon"),
                "Blessing Free Text Field", By.id("greeting-b2c-textarea"),
                "Add Picture or Video Button", By.id("add-pic-selection_button__input"),
                "Add Built-in Video Button", By.id("custom-selection-button"),
                "Continue Button", By.cssSelector("button[type=\"submit\"]")
        );
    }
}