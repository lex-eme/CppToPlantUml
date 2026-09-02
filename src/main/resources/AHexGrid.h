#pragma once

#include "CoreMinimal.h"
#include "Hex.h"
#include "Tile.h"
#include "TileDescriptor.h"
#include "GameFramework/Actor.h"
#include "HexGrid.generated.h"

class AEnemy;
class AResource;
class ABuilding;
class AUnit;
class UMapData;
class UTextRenderComponent;

UENUM()
enum class ETileDebugType : uint8
{
	None UMETA(DisplayName="None"),
	Cube UMETA(DisplayName="Cube"),
	Offset UMETA(DisplayName="Offset"),
	Weight UMETA(DisplayName="Weight"),
	Probability UMETA(DisplayName="Probability"),
};

UCLASS()
class TRPG_PROJET_API AHexGrid : public AActor
{
	GENERATED_BODY()

public:
	UPROPERTY(VisibleAnywhere, BlueprintReadOnly)
	FVector TopLeftBound;
	UPROPERTY(VisibleAnywhere, BlueprintReadOnly)
	FVector BottomRightBound;
	UPROPERTY()
	TArray<FHex> StartHexes;
	UPROPERTY(EditAnywhere, BlueprintReadWrite)
	UMapData* Map;
	UPROPERTY()
	TArray<FHex> Path;

protected:
	UPROPERTY(EditAnywhere, BlueprintReadWrite)
	float TileSize = 100.0f;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
	UStaticMesh* GroundMesh;
	UPROPERTY(EditAnywhere)
	UMaterialInterface* GroundMaterial;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
	UStaticMesh* GridMesh;
	UPROPERTY(EditAnywhere)
	UMaterialInterface* GridMaterial;

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly)
	TMap<FHex, FTile> Tiles;

	UPROPERTY(EditAnywhere, BlueprintReadWrite, Category="Debug")
	ETileDebugType DebugCoord = ETileDebugType::None;

	UPROPERTY(EditAnywhere)
	TSubclassOf<AResource> ResourceBlueprint;
	UPROPERTY(EditAnywhere)
	TSubclassOf<ABuilding> BuildingBlueprint;
	UPROPERTY(EditAnywhere)
	TSubclassOf<AUnit> UnitBlueprint;

private:
	UPROPERTY()
	UInstancedStaticMeshComponent* Ism;
	UPROPERTY()
	UInstancedStaticMeshComponent* GridIsm;

	UPROPERTY()
	TMap<FHex, UTextRenderComponent*> TextRenderComponents;
	int LastTotalWeight = 1;

public:
	AHexGrid();

	void SetMapData(UMapData* NewMapData);

	bool HasTile(FHex Hex) const;
	bool GetTile(FHex Hex, FTile& OutTile);
	void ComputeWeights();
	bool GetRandomWeightedTile(FHex& FoundHex);
	FHex GetHex(int32 Index) const;
	FHex GetHex(const FVector& Location) const;
	void GetNeighborHexes(FHex Hex, TArray<FHex>& OutNeighbors) const;
	TSet<FTile> GetNeighbors(const FTile& Tile);
	FVector GetTileLocation(FHex Hex) const;

	void AddTileState(FHex Hex, ETileState State);
	void RemoveTileState(FHex Hex, ETileState State);

	void AddEntity(AEntity* Entity, FHex To);
	void SendEntity(AUnit* Unit, FHex To);
	void MoveEntity(AUnit* Unit, FHex To);
	void RemoveEntity(FHex Location);

	void SpawnEnemy(FEnemyDescriptor Descriptor, AEnemy* Enemy);

	bool SearchPath(FHex From, FHex To);

	UFUNCTION(CallInEditor, Category="Debug")
	void Refresh();

protected:
	virtual void BeginPlay() override;
	virtual void OnConstruction(const FTransform& Transform) override;

	UPROPERTY(EditAnywhere, Category="Debug")
	FOffsetCoord DebugFindTileCoord;
	UPROPERTY(VisibleAnywhere, Category="Debug")
	FTile DebugTile;
	UFUNCTION(CallInEditor, Category="Debug")
	void DebugFindTile();


private:
	void SpawnTile(const FTileDescriptor& Descriptor);
	void ComputeBounds();
	void GetTileColor(FHex Hex, FLinearColor& Color);
	void UpdateTileColor(FHex Hex);
	static void SetIsmColor(UInstancedStaticMeshComponent* Ism, int Index, const FLinearColor& Color);
	TMap<FHex, int> TileWeights;
};
