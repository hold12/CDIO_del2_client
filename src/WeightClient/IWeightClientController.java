package WeightClient;

import java.io.IOException;

/**
 * Created by AndersWOlsen on 16-03-2017.
 */
public interface IWeightClientController {
    enum KeyPadState {
        LOWER_CHARS,UPPER_CHARS, NUMERIC;

        @Override
        public String toString() {
            switch (this) {
                case UPPER_CHARS: return "&1";
                case LOWER_CHARS: return "&2";
                case NUMERIC:     return "&3";
                default:          return "&3";
            }
        }
    }

    void connect(String host, int port) throws IOException;
    String getCurrentWeight() throws IOException;
    String tareWeight() throws IOException;
    void writeToPrimaryDisplay(String message) throws IOException;
    void writeToSecondaryDisplay(String message) throws IOException, StringIndexOutOfBoundsException;
    void clearPrimaryDisplay() throws IOException;
    String rm208(String primaryDisplay, String secondaryDisplay, KeyPadState keyPadState) throws IOException, StringIndexOutOfBoundsException;
//    void changeButtonState() throws IOException;
    void setNewGrossWeight(double newWeight) throws IOException;
    void cancelCurrentOperation() throws IOException;
    void close() throws IOException;
}
