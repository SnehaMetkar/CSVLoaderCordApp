
package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.CSVContract;
import com.template.contracts.TemplateContract;
import com.template.states.CSVState;
import net.corda.core.flows.*;
import net.corda.core.identity.AbstractParty;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.identity.Party;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.StateAndContract;
import net.corda.core.contracts.TimeWindow;
import net.corda.core.crypto.SecureHash;
import net.corda.core.crypto.TransactionSignature;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;
import net.corda.core.utilities.UntrustworthyData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UploadDataFlow extends FlowLogic<Void> {
    private final File csvFile;

    public UploadDataFlow(File csvFile) {
        this.csvFile = csvFile;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // Load the CSV data from the file
        List<String> csvData = loadCSVData(csvFile);


        if (csvData.size() < 3) {
            throw new FlowException("CSV data is incomplete");
        }

        long accountNumber = Long.parseLong(csvData.get(0));
        long invoiceNumber = Long.parseLong(csvData.get(1));
        String name = csvData.get(2);
        Date invoiceDate = null;
        try {
            invoiceDate = parseDate(csvData.get(3));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        double callDuaration = Double.parseDouble(csvData.get(4));
        double rate = Double.parseDouble(csvData.get(5));
        // Check and validate the data (implement your validation logic)

        // Define the notary and parties involved
        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
        AbstractParty myParty = getOurIdentity();

// Define the `AbstractParty` of other participants as needed
        AbstractParty participantParty1 = someOtherParty;
        AbstractParty participantParty2 = anotherParty;

// Create a list of participants (AbstractParty)
        List<AbstractParty> participants = Arrays.asList(myParty, participantParty1, participantParty2);

        // Create a new Corda state with the CSV data
        CSVState newState = new CSVState(accountNumber,invoiceNumber,name,invoiceDate,callDuaration,rate, participants);

        // Build the transaction
        TransactionBuilder txBuilder = new TransactionBuilder(notary)
                .addOutputState(newState, CSVContract.CONTRACT_ID)
                .addCommand(new Command<>(new CSVContract.Commands.UploadData(), participants));

        // Verify and sign the transaction
        txBuilder.verify(getServiceHub());
        SignedTransaction signedTx = getServiceHub().signInitialTransaction(txBuilder);

        // Finalize the transaction
        subFlow(new FinalityFlow(signedTx,Collections.emptyList()));

        return null;
    }

    private List<String> loadCSVData(File file) throws FlowException {
        List<String> data = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
        } catch (Exception e) {
            throw new FlowException("Error loading CSV data: " + e.getMessage());
        }
        return data;
    }

    private Date parseDate(String dateStr) throws ParseException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Change the format as needed
        return dateFormat.parse(dateStr);
    }
}
