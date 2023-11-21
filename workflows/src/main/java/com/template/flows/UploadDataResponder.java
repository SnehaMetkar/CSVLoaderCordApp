package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;

import java.util.List;

public class UploadDataResponder extends FlowLogic<Void> {
    private final FlowSession otherPartySession;

    public UploadDataResponder(FlowSession otherPartySession) {
        this.otherPartySession = otherPartySession;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // Respond to the upload request as needed (e.g., verifying data, additional checks).
        // You can also save the data to your local database or perform other actions.

        // If you need to send a response to the initiating party, you can use send() or sendAndReceive()
        // Example: otherPartySession.send("Data upload successful");

        return null;
    }
}
