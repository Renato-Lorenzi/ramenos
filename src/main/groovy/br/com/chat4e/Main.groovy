package br.com.chat4e

import br.com.chat4e.views.ChatViewerFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.events.ShellAdapter
import org.eclipse.swt.events.ShellEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*

class Main {


    static void main(String[] ags) {
        Display display = new Display();
        Shell shell = new Shell(display);

        shell.addShellListener(new ShellAdapter() {

            @Override
            void shellDeactivated(ShellEvent e) {
                shell.visible = false
            }

        })
        trayIcon(shell, display)
        shell.setSize(300, 500);
        shell.setText("Ramenos messenger");
        shell.setLayout(new FillLayout());
        def parent = new Composite(shell, SWT.NONE)
        parent.setLayout(new GridLayout(1, false))
        parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true))
        def chatView = ChatViewerFactory.createChatViewPart()
        chatView.createPartControl(parent)
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }


    static trayIcon(Shell shell, Display display) {
        Image image = new Image(display, 16, 16);
        final Tray tray = display.getSystemTray();
        if (tray == null) {
            System.out.println("The system tray is not available");
        } else {
            final TrayItem item = new TrayItem(tray, SWT.NONE);
            item.setToolTipText("Ramenos messenger");
            item.setImage(image);
            item.addSelectionListener(new SelectionAdapter() {
                @Override
                void widgetSelected(SelectionEvent e) {
                    shell.visible = true
                }
            })
        }
    }

}
