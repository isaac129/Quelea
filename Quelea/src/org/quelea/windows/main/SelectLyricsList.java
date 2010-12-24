package org.quelea.windows.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import org.quelea.display.SongSection;
import org.quelea.utils.QueleaProperties;

/**
 * A list displaying the different sections in the song.
 * @author Michael
 */
public class SelectLyricsList extends JList {

    private final Color originalSelectionColour;

    /**
     * Used for displaying summaries of items in the service in the schedule
     * list.
     */
    private static class SelectLyricsRenderer extends JLabel implements ListCellRenderer {

        /**
         * @inheritDoc
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (!(value instanceof SongSection)) {
                return new JLabel();
            }
            setBorder(new EmptyBorder(5, 5, 5, 5));
            SongSection section = (SongSection) value;
            StringBuilder labelHTML = new StringBuilder();
            labelHTML.append("<html>");
            if(!section.getTitle().trim().equals("")) {
                labelHTML.append("<font color=\"white\"><span style=\"background-color:blue; width:100%;\">&nbsp;");
                labelHTML.append(section.getTitle());
                labelHTML.append("&nbsp;</span></font><br/>");
            }
            for(String line : section.getLyrics()) {
                labelHTML.append(line);
                labelHTML.append("<br/>");
            }
            labelHTML.append("</html>");
            setText(labelHTML.toString());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;

        }
    }

    /**
     * Create a new schedule list with a given model.
     * @param model the model to display.
     */
    public SelectLyricsList(DefaultListModel model) {
        super(model);
        originalSelectionColour = getSelectionBackground();
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if(getModel().getSize()>0) {
                    setSelectionBackground(QueleaProperties.get().getActiveSelectionColor());
                }
            }

            public void focusLost(FocusEvent e) {
                setSelectionBackground(originalSelectionColour);
            }
        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new SelectLyricsRenderer());
    }

    /**
     * @inheritDoc
     * @return a DefaultListModel that backs this lyrics list.
     */
    @Override
    public DefaultListModel getModel() {
        return (DefaultListModel)super.getModel();
    }


}
