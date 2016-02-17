package co.edu.uniandes.csw.crud.api.podam.strategy;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * Strategy to generate random Date instances compatible with DerbyDB Date fields
 * @author af.esguerra10
 */
public class DateStrategy implements AttributeStrategy<Date> {

    @Override
    public Date getValue() {
        Random r = new Random();
        Calendar c = Calendar.getInstance();
        int max_year = 9999;
        c.set(Calendar.YEAR, r.nextInt(
                max_year - c.getActualMinimum(Calendar.YEAR) + 1)
                + c.getActualMinimum(Calendar.YEAR));
        c.set(Calendar.DAY_OF_YEAR, r.nextInt(
                c.getActualMaximum(Calendar.DAY_OF_YEAR) - c.getActualMinimum(Calendar.DAY_OF_YEAR) + 1)
                + c.getActualMinimum(Calendar.DAY_OF_YEAR));
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }
}
