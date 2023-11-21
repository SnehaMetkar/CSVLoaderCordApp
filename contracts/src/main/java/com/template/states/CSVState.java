package com.template.states;

import com.template.contracts.TemplateContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

// *********
// * State *
// *********
@BelongsToContract(TemplateContract.class)
public class CSVState implements ContractState {

    //private variables
    private final long AccountNumber;

    private final long InvoiceNumber;

    private final String AccountName;

    private final Date InvoiceDate;

    private final Double CallDuration;


    private final Double Rate;
    private final List<AbstractParty> participants;
    public CSVState(long accountNumber, long invoiceNumber, String accountName, Date invoiceDate, Double callDuration, Double rate, List<AbstractParty> participants) {
        this.AccountNumber = accountNumber;
        this.InvoiceNumber = invoiceNumber;
        this.AccountName = accountName;
        this.InvoiceDate = invoiceDate;
        this.CallDuration = callDuration;

        this.Rate = rate;
        this.participants = participants;
    }

    //getters
    /* public Party getSender() { return sender; }
    public Party getReceiver() { return receiver; }

    /* This method will indicate who are the participants and required signers when
     * this state is used in a transaction. */
    @Override
    public List<AbstractParty> getParticipants() {
        return participants;
    }

    public Double getRate() {
        return Rate;
    }

    public Double getCallDuration() {
        return CallDuration;
    }

    public Date getInvoiceDate() {
        return InvoiceDate;
    }

    public String getAccountName() {
        return AccountName;
    }

    public long getInvoiceNumber() {
        return InvoiceNumber;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }
}