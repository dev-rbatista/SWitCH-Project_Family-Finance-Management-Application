package switchtwentytwenty.project.domain.valueobject;

import switchtwentytwenty.project.util.DateHelper;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class RegistrationDate implements ValueObject {

    private Calendar registrationDate;

    /**
     * Constructor for a RegistrationDate Object, if the argument registrationDate is null the registration date will be the current date
     *
     * @param registrationDate a LocalDate object that will represent the date of the registration
     */
    public RegistrationDate(String registrationDate) {
        this.registrationDate = DateHelper.parseDateToCalendar(registrationDate);
        if (!validateDate()) {
            this.registrationDate = Calendar.getInstance();
        }
    }

    /**
     * Method to determine if a date is valid, i.e. not null
     *
     * @return boolean - returns true if the date is valid, false if it is null
     */
    private boolean validateDate() {
        return registrationDate != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationDate)) return false;
        RegistrationDate that = (RegistrationDate) o;
        return registrationDate.equals(that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationDate);
    }
}
