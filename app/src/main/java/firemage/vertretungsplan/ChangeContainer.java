package firemage.vertretungsplan;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by flose on 25.07.2017.
 */

public class ChangeContainer implements Comparable<ChangeContainer> {
    public String schoolClass;
    public String hour;
    public String lesson;
    public String room;
    public String actionType;
    public String replace;
    public String note;
    public int minHour;

    public ChangeContainer(String schoolClass, String hour, String lesson, String room,
                           String actionType, String replace, String note) {
        this.schoolClass = schoolClass;
        this.hour = hour;
        this.lesson = lesson;
        this.room = room;
        this.replace = replace;
        this.note = note;
        this.actionType = actionType;

        ArrayList<Integer> list = Utils.getAllNumbers(hour);
        Collections.sort(list);
        if(list.size()>0)
            minHour = list.get(0);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(schoolClass);
        builder.append(":   ");
        appendIfNecessary(hour, builder);
        appendIfNecessary(lesson, builder);
        appendIfNecessary(room, builder);
        appendIfNecessary(actionType, builder);
        appendIfNecessary(replace, builder);
        if(note != null && note.length() != 0) {
            builder.append("  (");
            builder.append(note);
            builder.append(")");
        }
        return builder.toString();
    }

    private void appendIfNecessary(String str, StringBuilder builder) {
        if(str != null && str.length() != 0) {
            builder.append(str);
            builder.append("   ");
        }
    }

    @Override
    public int compareTo(ChangeContainer cont) {

        return ((Integer) minHour).compareTo(cont.minHour);
    }

    @Override
    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (o == this)
            return true;

        if (!(o instanceof ChangeContainer))
            return false;

        ChangeContainer cont = (ChangeContainer) o;

        return cont.actionType.equals(this.actionType) && cont.hour.equals(this.hour) && cont.lesson.equals(this.lesson)
                && cont.room.equals(this.room) && cont.replace.equals(this.replace) && cont.note.equals(this.note);
    }
}
