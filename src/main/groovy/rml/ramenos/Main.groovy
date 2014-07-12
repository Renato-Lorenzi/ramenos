package rml.ramenos

import br.com.chat4e.views.ChatViewerFactory
import br.com.chat4e.views.core.ChatViewShow
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

class Main {

    static void main(String[] ags) {
        Display display = new Display();
        Shell shell = new Shell(display);
//        shell.setSize(300, 200);
        shell.setText("Button Example");
        shell.setLayout(new FillLayout());
        def parent = new Composite(shell, SWT.NONE)
        parent.setLayout(new GridLayout(1, false))
        parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true))
//        def parentLeft = new Composite(parent, SWT.NONE)
//        parentLeft.layoutData = new GridData(SWT.LEFT, SWT.TOP, true, true)
//        def parentRight = new Composite(parent, SWT.NONE)
//        parentRight.layoutData = new GridData(SWT.LEFT, SWT.TOP, true, true)

        def accountView = ChatViewerFactory.createAccountViewPart({} as ChatViewShow)
        accountView.createPartControl(parent)
        def chatView = ChatViewerFactory.createChatViewPart({} as ChatViewShow)
        chatView.createPartControl(parent)

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
