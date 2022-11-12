<script>
	import welcome from '$lib/images/svelte-welcome.webp';
	import welcome_fallback from '$lib/images/svelte-welcome.png';
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="Svelte demo app" />
</svelte:head>

<section>
    <h1 class="text-3xl font-bold underline">
	    <form method="post" enctype="multipart/form-data" action="http://localhost:18000/ipfs/upload">
			<input type="file" name="file">
			<input type="textarea" name="otherField">
			<input type="submit">
		</form>
	</h1>


</section>

<style>

</style>
