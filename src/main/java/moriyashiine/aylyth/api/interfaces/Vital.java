package moriyashiine.aylyth.api.interfaces;

import java.util.Optional;

public interface Vital {
    static Optional<Vital> of(Object context) {
        if (context instanceof Vital) {
            return Optional.of(((Vital) context));
        }
        return Optional.empty();
    }

    int getVitalThuribleLevel();

    void setVitalThuribleLevel(int vital);

}
