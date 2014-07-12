package br.com.chat4e.views.others

import org.eclipse.jface.viewers.Viewer
import org.eclipse.jface.viewers.ViewerFilter
import rml.ramenos.messager.Buddy

public class BuddyViewFilter extends ViewerFilter {

    public static String searchPattern = "";

    @Override
    public boolean select(Viewer viewer, Object parent, Object element) {

        if (searchPattern != null && !searchPattern.equals("")) {
            if (element instanceof Buddy) {
                return (((Buddy) element).user.toLowerCase()
                        .contains(searchPattern.toLowerCase()) || ((Buddy) element)
                        .machine.toLowerCase()
                        .contains(searchPattern.toLowerCase())) ? true : false;
            }
        }
        return true;

    }
}