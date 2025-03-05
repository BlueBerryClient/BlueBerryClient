package xyz.blackdev.Blueberry.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtil {

    public static void copyToClipboard(String text) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Cannot access clipboard in a headless environment.");
            return;
        }
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}