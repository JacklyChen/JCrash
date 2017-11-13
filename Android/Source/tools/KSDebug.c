#include "KSDebug.h"

//#define KSLogger_LocalLevel TRACE
#include "KSLogger.h"

#include <errno.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>


/** Check if the current process is being traced or not.
 *
 * @return true if we're being traced.
 */
bool ksdebug_isBeingTraced(void)
{
    const char* filename = "/proc/self/status";
    int fd = open(filename, O_RDONLY);
    if(fd < 0)
    {
        KSLOG_ERROR("Error opening %s: %s", filename, strerror(errno));
        return false;
    }

    char buffer[1000];
    int bytesRead = read(fd, buffer, sizeof(buffer) - 1);
    close(fd);
    if(bytesRead <= 0)
    {
        KSLOG_ERROR("Error reading %s: %s", filename, strerror(errno));
        return false;
    }

    buffer[bytesRead] = 0;
    const char tracerPidText[] = "TracerPid:";
    const char* tracerPointer = strstr(buffer, tracerPidText);
    if(tracerPointer == NULL)
    {
        return false;
    }

    tracerPointer += sizeof(tracerPidText);
    return atoi(tracerPointer) > 0;
}
