package com.gamergrotte.core.windows.task.scheduler.configuration;

import com.gamergrotte.core.windows.task.scheduler.TaskService;
import lombok.*;

/**
 * Language Configuration for the {@link TaskService} Class
 * <p>Translation of the return Messages
 *
 * <p>Standard Language is German
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageConfiguration {

    private String SuccessMessage = "ERFOLGREICH";

    private String ErrorMessage = "FEHLER";

    private String FieldNotDefined = "Nicht zutreffend";

}
