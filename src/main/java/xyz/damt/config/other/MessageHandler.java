package xyz.damt.config.other;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.damt.util.CC;

public class MessageHandler {

    public final String DEATH_MESSAGE;
    public final String BALANCE_MESSAGE;
    public final String PAY_SENT;
    public final String PAY_PAID;

    public MessageHandler(FileConfiguration soup) {
        this.DEATH_MESSAGE = CC.translate(soup.getString("death-message"));
        this.BALANCE_MESSAGE = CC.translate(soup.getString("balance-message"));
        this.PAY_SENT = CC.translate(soup.getString("pay.sent"));
        this.PAY_PAID = CC.translate(soup.getString("pay.paid"));
    }

}
