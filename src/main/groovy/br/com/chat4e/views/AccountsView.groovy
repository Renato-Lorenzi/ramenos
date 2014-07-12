package br.com.chat4e.views

import br.com.chat4e.views.core.ChatViewPart
import br.com.chat4e.views.core.ChatViewShow
import br.com.chat4e.views.others.BuddyViewFilter
import org.eclipse.jface.action.Action
import org.eclipse.jface.viewers.*
import org.eclipse.swt.SWT
import org.eclipse.swt.events.FocusEvent
import org.eclipse.swt.events.FocusListener
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.ModifyListener
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Text
import rml.ramenos.messager.Buddy
import rml.ramenos.messager.Messenger

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
class AccountsView implements ChatViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "com.saffronr.br.com.chat4e.views.AccountsView"
    private TreeViewer viewer
    public static Action about
    private Action startChat
    ViewContentProvider contentProvider
    private Text searchBox
    SWTChatViewController chatvieweController
    Messenger messager
    private ChatViewShow chatViewController

    /**
     * The constructor.
     *
     * @param chatViewController
     */
    public AccountsView(ChatViewShow chatViewController) {
        this.chatViewController = chatViewController
        chatvieweController = SWTChatViewController
                .getInstance(chatViewController)
        messager = chatvieweController.getMessager()
        chatvieweController.setAccountsView(this)
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout(1, true))
        searchBox = new Text(parent, SWT.SEARCH | SWT.ICON_CANCEL
                | SWT.ICON_SEARCH)
        searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL))
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.V_SCROLL)
        viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH))
        BuddyViewFilter[] filters = [new BuddyViewFilter()];
        viewer.setFilters(filters)
        contentProvider = new ViewContentProvider()
        contentProvider.setRoot(new ArrayList(messager.getBuddies().values()).toArray())
        viewer.setContentProvider(contentProvider)
        viewer.setLabelProvider(new LabelProvider())
        viewer.setInput(messager.getBuddies())
        makeActions()
        hookDoubleClickAction()
    }

    private void makeActions() {
        startChat = new Action() {
            @Override
            public void run() {
                chatViewController.showView()
                Buddy b = (Buddy) ((TreeSelection) (viewer.getSelection()))
                        .getFirstElement()
                chatvieweController.sendChat(b, null)
            }
        }
        searchBox.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent arg0) {
                searchBox.setSelection(0, searchBox.getText().length())
                searchBox.redraw()
            }

            public void focusLost(FocusEvent arg0) {

            }

        })

        searchBox.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent arg0) {
                BuddyViewFilter.searchPattern = searchBox.getText()
                viewer.refresh(true)
            }

        })

    }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                startChat.run()
            }
        })
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        viewer.getControl().setFocus()
    }

    public void refresh() {
        viewer.refresh(true)
    }
}