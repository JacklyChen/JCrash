
#include "KSDynamicLinker.h"

#include <limits.h>
#include <string.h>

#include "KSLogger.h"

uint32_t ksdl_imageNamed(const char *const imageName, bool exactMatch) {
    // TODO
    return UINT32_MAX;
}

const uint8_t *ksdl_imageUUID(const char *const imageName, bool exactMatch) {
    // TODO
    return NULL;
}

bool ksdl_dladdr(const uintptr_t address, Dl_info *const info) {
    return dladdr((void *) address, info);
}

int ksdl_imageCount() {
    // TODO
    return 0;
}

bool ksdl_getBinaryImage(int index, KSBinaryImage *buffer) {
    // TODO
    return false;
}
