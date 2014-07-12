package br.com.chat4e.views


import org.eclipse.jface.viewers.IStructuredContentProvider
import org.eclipse.jface.viewers.ITreeContentProvider
import org.eclipse.jface.viewers.Viewer
import rml.ramenos.messager.Buddy

/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author Rajgopal Vaithiyanathan
 *
 */
class ViewContentProvider implements IStructuredContentProvider,
        ITreeContentProvider {
    private Buddy[] children

    public ViewContentProvider() {
    }

    public void inputChanged(Viewer v, Object oldInput, Object newInput) {

    }

    public void dispose() {
    }

    public void setRoot(Object[] children) {
        this.children = children
    }

    public Object[] getElements(Object parent) {
        return children
    }

    public Object getParent(Object child) {
        return children
    }

    public Object[] getChildren(Object parent) {
        return children
    }

    public boolean hasChildren(Object parent) {
        return parent instanceof Object[]
    }
}
