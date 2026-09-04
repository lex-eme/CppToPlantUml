#pragma once

#include "CoreMinimal.h"
#include "Hex.generated.h"

/**
 *
 */
USTRUCT(BlueprintType)
struct TRPG_PROJET_API FHex
{
	GENERATED_BODY()

    int x, y;
	static FHex Directions[6];
};